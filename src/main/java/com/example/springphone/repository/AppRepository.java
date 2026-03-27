package com.example.springphone.repository;

import com.example.springphone.entity.AppEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppRepository extends JpaRepository<AppEntity, Long> {

    List<AppEntity> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    List<AppEntity> findByCategoryIgnoreCaseOrderByNameAsc(String category);

    List<AppEntity> findByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByNameAsc(String name, String category);

    @Query("""
        select a
        from AppEntity a
        where not exists (
            select 1
            from PurchaseEntity p
            where p.user.id = :userId
              and p.app.id = a.id
        )
        order by a.name asc
        """)
    List<AppEntity> findAvailableAppsForUser(@Param("userId") Long userId);

    @Query("""
        select a
        from AppEntity a
        where lower(a.category) = lower(:category)
          and not exists (
              select 1
              from PurchaseEntity p
              where p.user.id = :userId
                and p.app.id = a.id
          )
        order by a.name asc
        """)
    List<AppEntity> findAvailableAppsForUserByCategory(@Param("userId") Long userId, @Param("category") String category);

    @Query("""
        select a
        from AppEntity a
        where lower(a.name) like lower(concat('%', :name, '%'))
          and not exists (
              select 1
              from PurchaseEntity p
              where p.user.id = :userId
                and p.app.id = a.id
          )
        order by a.name asc
        """)
    List<AppEntity> findAvailableAppsForUserByName(@Param("userId") Long userId, @Param("name") String name);

    @Query("""
        select a
        from AppEntity a
        where lower(a.name) like lower(concat('%', :name, '%'))
          and lower(a.category) = lower(:category)
          and not exists (
              select 1
              from PurchaseEntity p
              where p.user.id = :userId
                and p.app.id = a.id
          )
        order by a.name asc
        """)
    List<AppEntity> findAvailableAppsForUserByNameAndCategory(
        @Param("userId") Long userId,
        @Param("name") String name,
        @Param("category") String category
    );
}
