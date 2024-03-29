package com.example.conference.services;

import com.example.conference.exceptions.ResourceNotFoundException;
import com.example.conference.models.dtos.CreateConferenceDto;
import com.example.conference.models.dtos.UpdateConferenceDto;
import com.example.conference.models.viewmodels.ConferenceVm;
import com.example.conference.repositories.ConferenceRepository;
import com.example.conference.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceService implements IConferenceService{
    private final ConferenceRepository conferenceRepository;
    private final LocationRepository locationRepository;

    public ConferenceService(ConferenceRepository conferenceRepository, LocationRepository locationRepository) {
        this.conferenceRepository = conferenceRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public ConferenceVm getById(Long id) {
        return conferenceRepository.findById(id)
                .map(ConferenceVm::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Conference with id " + id + " not found."));
    }

    @Override
    public List<ConferenceVm> getAll() {
        return conferenceRepository.findAll().stream()
                .map(ConferenceVm::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ConferenceVm create(CreateConferenceDto createConferenceDto) {
        var entity = createConferenceDto.toEntity();
        var saved = conferenceRepository.save(entity);
        return ConferenceVm.fromEntity(saved);
    }

    @Override
    public ConferenceVm update(Long id, UpdateConferenceDto updateConferenceDto) {
        var entity = conferenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conference with id " + id + " not found."));

        updateConferenceDto.updateEntity(entity);
        var saved = conferenceRepository.save(entity);
        return ConferenceVm.fromEntity(saved);
    }

    @Override
    public void delete(Long id) {
        try {
            conferenceRepository.deleteById(id);
        } catch (Exception ignored) {
            throw new ResourceNotFoundException("Conference with id " + id + " not found.");
        }
    }

    @Override
    public void addLocation(Long conferenceId, Long locationId) {
        var conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Conference with id " + conferenceId + " not found."));
        var location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + conferenceId + " not found."));
        conference.addLocation(location);
        conferenceRepository.save(conference);
    }

    @Override
    public void removeLocation(Long conferenceId, Long locationId) {
        var conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Conference with id " + conferenceId + " not found."));
        var location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + conferenceId + " not found."));
        conference.removeLocation(location);
        conferenceRepository.save(conference);
    }

    @Override
    public List<ConferenceVm> getAllByLocationId(Long locationId){
        return conferenceRepository.findAllByLocationId(locationId)
                .stream()
                .map(ConferenceVm::fromEntity)
                .collect(Collectors.toList());
    }
}
