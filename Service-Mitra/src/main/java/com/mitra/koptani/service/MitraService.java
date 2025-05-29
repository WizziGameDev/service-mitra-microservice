package com.mitra.koptani.service;

import com.mitra.koptani.dto.MitraListResponse;
import com.mitra.koptani.dto.MitraRequest;
import com.mitra.koptani.dto.MitraResponse;

import java.util.List;

public interface MitraService {

    // Get List Mitra
    List<MitraListResponse> getMitraList();

    // Get By ID
    MitraResponse getMitraById(Integer id);

    // Add Mitra
    MitraResponse addMitra(MitraRequest mitraRequest);

    // Update Mitra By ID and Request
    MitraResponse updateMitra(Integer id, MitraRequest mitraRequest);

    // Delete Mitra By ID
    String deleteMitra(Integer id);
}
