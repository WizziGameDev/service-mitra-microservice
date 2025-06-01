package com.mitra.koptani.service;

import com.mitra.koptani.dto.MitraListResponse;
import com.mitra.koptani.dto.MitraRequest;
import com.mitra.koptani.dto.MitraResponse;
import com.mitra.koptani.entity.Mitra;
import com.mitra.koptani.exception.MitraException;
import com.mitra.koptani.repository.MitraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MitraServiceImpl implements MitraService {

    @Autowired
    private MitraRepository mitraRepository;

    @Override
    @Cacheable(value = "MitraService.getMitraList", key = "'mitras'")
    public List<MitraListResponse> getMitraList() {
        return mitraRepository.findAllByDeletedAtIsNull().stream()
                .map(data -> MitraListResponse.builder()
                        .id(data.getId())
                        .slug(data.getSlug())
                        .name(data.getName())
                        .phoneNumber(data.getPhoneNumber())
                        .type(data.getType())
                        .status(data.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "MitraService.getMitraById", key = "#id")
    public MitraResponse getMitraById(Integer id) {
        Mitra mitra = mitraRepository.findAllByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new MitraException("Mitra Not Found"));

        return MitraResponse.builder()
                .id(mitra.getId())
                .slug(mitra.getSlug())
                .name(mitra.getName())
                .email(mitra.getEmail())
                .phoneNumber(mitra.getPhoneNumber())
                .address(mitra.getAddress())
                .type(mitra.getType())
                .status(mitra.getStatus())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "MitraService.getMitraList", key = "'mitras'")
    public MitraResponse addMitra(MitraRequest mitraRequest) {

        Mitra mitra = new Mitra();
        mitra.setSlug(slugify(mitraRequest.getName()));
        mitra.setName(mitraRequest.getName());
        mitra.setEmail(mitraRequest.getEmail());
        mitra.setPhoneNumber(mitraRequest.getPhoneNumber());
        mitra.setAddress(mitraRequest.getAddress());
        mitra.setType(mitraRequest.getType());
        mitra.setStatus(mitraRequest.getStatus());
        mitra.setCreatedAt(Instant.now().getEpochSecond());

        Mitra saveMitra = mitraRepository.save(mitra);

        return MitraResponse.builder()
                .id(saveMitra.getId())
                .slug(saveMitra.getSlug())
                .name(saveMitra.getName())
                .email(saveMitra.getEmail())
                .phoneNumber(saveMitra.getPhoneNumber())
                .address(saveMitra.getAddress())
                .type(saveMitra.getType())
                .status(saveMitra.getStatus())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "MitraService.getMitraList", key = "'mitras'"),
            @CacheEvict(value = "MitraService.getMitraById", key = "#id")
    })
    public MitraResponse updateMitra(Integer id, MitraRequest mitraRequest) {
        Mitra mitra = mitraRepository.findAllByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new MitraException("Mitra Not Found"));

        mitra.setSlug(slugify(mitraRequest.getName()));
        mitra.setName(mitraRequest.getName());
        mitra.setEmail(mitraRequest.getEmail());
        mitra.setPhoneNumber(mitraRequest.getPhoneNumber());
        mitra.setAddress(mitraRequest.getAddress());
        mitra.setType(mitraRequest.getType());
        mitra.setStatus(mitraRequest.getStatus());
        mitra.setUpdatedAt(Instant.now().getEpochSecond());

        Mitra saveMitra = mitraRepository.save(mitra);

        return MitraResponse.builder()
                .id(saveMitra.getId())
                .slug(saveMitra.getSlug())
                .name(saveMitra.getName())
                .email(saveMitra.getEmail())
                .phoneNumber(saveMitra.getPhoneNumber())
                .address(saveMitra.getAddress())
                .type(saveMitra.getType())
                .status(saveMitra.getStatus())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "MitraService.getMitraList", key = "'mitras'"),
            @CacheEvict(value = "MitraService.getMitraById", key = "#id")
    })
    public String deleteMitra(Integer id) {
        Mitra mitra = mitraRepository.findAllByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new MitraException("Mitra Not Found"));

        mitra.setDeletedAt(Instant.now().getEpochSecond());
        mitraRepository.save(mitra);

        return "Successfully deleted Mitra";
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "MitraService.getMitraList", key = "'mitras'"),
            @CacheEvict(value = "MitraService.getMitraById", key = "#id")
    })
    public String updateStatusMitra(Integer id, String status) {
        Mitra mitra = mitraRepository.findAllByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new MitraException("Mitra Not Found"));

        mitra.setStatus(status);
        mitraRepository.save(mitra);

        return "Successfully updated Mitra Status";
    }

    public static String slugify(String input) {
        String baseSlug = input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");

        int randomNumber = new Random().nextInt(9000) + 1000; // random 4 digit antara 1000-9999
        return baseSlug + "-" + randomNumber;
    }

}
