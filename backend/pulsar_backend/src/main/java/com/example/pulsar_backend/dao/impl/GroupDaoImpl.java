package com.example.pulsar_backend.dao.impl;

import com.example.pulsar_backend.dao.IGroupDao;
import com.example.pulsar_backend.entity.GroupMasterEntity;
import com.example.pulsar_backend.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GroupDaoImpl implements IGroupDao {
    private final GroupRepository groupRepository;

    @Override
    public GroupMasterEntity save(GroupMasterEntity group) {
        log.debug("DAO: Saving group with name: {}", group.getName());
        return groupRepository.save(group);
    }

    @Override
    public List<GroupMasterEntity> saveAll(List<GroupMasterEntity> groups) {
        log.debug("DAO: Saving {} groups", groups.size());
        return groupRepository.saveAll(groups);
    }

    @Override
    public Optional<GroupMasterEntity> findById(Long id) {
        log.debug("DAO: Finding group by ID: {}", id);
        return groupRepository.findById(id);
    }

    @Override
    public List<GroupMasterEntity> findAll() {
        log.debug("DAO: Finding all groups");
        return groupRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("DAO: Deleting group by ID: {}", id);
        groupRepository.deleteById(id);
    }
}
