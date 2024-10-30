package com.raillylinker.springboot_mvc_template_private_java.data_sources.mongo_db_beans.mdb1_main.repositories;

import com.raillylinker.springboot_mvc_template_private_java.data_sources.mongo_db_beans.mdb1_main.documents.Mdb1Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mdb1TestRepository extends MongoRepository<Mdb1Test, String> {
    // 추가적인 쿼리 메서드가 필요한 경우 여기에 정의할 수 있습니다.
}