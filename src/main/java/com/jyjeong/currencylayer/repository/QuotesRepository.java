package com.jyjeong.currencylayer.repository;

import com.jyjeong.currencylayer.dto.QuotesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuotesRepository extends JpaRepository<QuotesDto,String> {

}
