package org.internal.hire.repository;

import java.util.List;
import java.util.Optional;
import org.internal.hire.entity.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BiodataRepository extends JpaRepository<Biodata, Long> {

    @Query("select b from Biodata b where b.user.id = :userId")
    Optional<Biodata> findByUserId(@Param("userId") Long userId);

    @Query("""
            select distinct b from Biodata b
            left join b.pendidikanList p
            where (:search is null or :search = ''
                or lower(b.nama) like lower(concat('%', :search, '%'))
                or lower(b.posisiDilamar) like lower(concat('%', :search, '%'))
                or lower(p.jenjang) like lower(concat('%', :search, '%')))
            order by b.createdAt desc
            """)
    List<Biodata> searchUser(@Param("search") String query);
}

