package com.repository;

        import com.model.Animal;
        import com.model.TreatmentRequest;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.PagingAndSortingRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;
        import java.util.Optional;


@Repository
public interface TreatmentReqDao extends PagingAndSortingRepository<TreatmentRequest, Integer> {

    @Query(value = "SELECT * FROM treatment_request r WHERE r.care_attn_id=?1", nativeQuery = true)
    List<TreatmentRequest> findRequestByCareAttnId(int careAttnId);

    @Query(value = "SELECT * FROM treatment_request r WHERE r.user_id=?1", nativeQuery = true)
    List<TreatmentRequest> findRequestByTechId(int techId);



}

