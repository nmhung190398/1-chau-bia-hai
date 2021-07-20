package net.devnguyen.service;

import net.devnguyen.dto.RoomDTO;
import net.devnguyen.dto.StudentDTO;
import net.devnguyen.entity.StudentEntity;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataService {

    public Set<RoomDTO> syncData() {
        Set<RoomDTO> rawData = getDataOtherService();


        Set<StudentDTO> students = rawData.parallelStream().flatMap(r -> r.getStudents().stream()).collect(Collectors.toSet());
        Set<UUID> roomIds = students.stream()
                .map(StudentDTO::getId).collect(Collectors.toSet());

        Map<UUID, StudentEntity> studentInDB = findByIds(roomIds)
                .stream().collect(Collectors.toMap(StudentEntity::getId, r -> r));

        //enrich data
        Map<UUID, List<StudentDTO>> studentGroupByRoom = students.parallelStream().peek(s -> {
            StudentEntity studentEntity = studentInDB.getOrDefault(s.getId(), null);
            if (studentEntity != null) {
                s.setPoint(studentEntity.getPoint());
            }
        }).filter(s -> s.getPoint() != null && s.getPoint().compareTo(s.getMinPoint()) < 0) // loai bo thang it diem
                .collect(Collectors.groupingBy(StudentDTO::getRoomId));


        rawData.forEach(r -> {
            r.setStudents(new HashSet<>(studentGroupByRoom.getOrDefault(r.getId(), new ArrayList<>())));
        });
        return rawData;
    }

    /**
     * get data
     *
     * @return
     */
    private Set<RoomDTO> getDataOtherService() {
        return new HashSet<>();
    }

    /**
     * select in db
     *
     * @param ids
     * @return
     */
    private List<StudentEntity> findByIds(Set<UUID> ids) {
        return new ArrayList<>();
    }
}
