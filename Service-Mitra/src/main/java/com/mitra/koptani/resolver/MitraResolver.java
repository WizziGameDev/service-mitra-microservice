package com.mitra.koptani.resolver;

import com.mitra.koptani.dto.MitraListResponse;
import com.mitra.koptani.dto.MitraRequest;
import com.mitra.koptani.dto.MitraResponse;
import com.mitra.koptani.service.MitraServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Controller
public class MitraResolver {

    @Autowired
    private MitraServiceImpl mitraServiceImpl;

    @QueryMapping
    public List<MitraListResponse> mitras() {
        return runVirtual(() -> mitraServiceImpl.getMitraList());
    }

    @QueryMapping
    public MitraResponse mitraById(@Argument Integer id) {
        return runVirtual(() -> mitraServiceImpl.getMitraById(id));
    }

    @MutationMapping
    public MitraResponse createMitra(@Argument("input") @Valid MitraRequest request) {
        return runVirtual(() -> mitraServiceImpl.addMitra(request));
    }

    @MutationMapping
    public MitraResponse updateMitra(@Argument Integer id,
                                     @Argument("input") @Valid MitraRequest request) {
        return runVirtual(() -> mitraServiceImpl.updateMitra(id, request));
    }

    @MutationMapping
    public String deleteMitra(@Argument Integer id) {
        return runVirtual(() -> mitraServiceImpl.deleteMitra(id));
    }

    private <T> T runVirtual(Supplier<T> task) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        Thread.startVirtualThread(() -> {
            try {
                result.complete(task.get());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        });

        try {
            return result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
