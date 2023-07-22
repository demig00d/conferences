package com.example.conference.services;

import com.example.conference.models.dtos.CreateConferenceDto;
import com.example.conference.models.dtos.UpdateConferenceDto;
import com.example.conference.models.dtos.UpdatePartiallyConferenceDto;
import com.example.conference.models.viewmodels.ConferenceVm;

import java.util.List;

public interface IConferenceService {
        ConferenceVm getById(Long id);
        List<ConferenceVm> getAll();
        ConferenceVm create(CreateConferenceDto createConferenceDto);
        ConferenceVm update(Long id, UpdateConferenceDto updateConferenceDto);
        ConferenceVm updatePartially(Long id, UpdatePartiallyConferenceDto updatePartiallyConferenceDto);
        void delete(Long id);
}
