package com.batch.pass.repository;

import com.batch.pass.entity.packaze.Package;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("package test")
class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;

    @Test
    public void save() {
        // given
        Package packge = new Package();
        packge.setPackageName("6개월 바디프로필 완성 패키지");
        packge.setPeriod(84);

        // when
        packageRepository.save(packge);

        // then
        assertNotNull(packge.getPackageSeq());
    }

    @Test
    public void findByCreatedAtAfter() {
        // given
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        Package pakage = new Package();
        pakage.setPackageName("학생 특별 할인 패키지");
        pakage.setPeriod(40);
        packageRepository.save(pakage);

        Package newPakage = new Package();
        newPakage.setPackageName("직장인 특별 할인 패키지");
        newPakage.setPeriod(50);
        packageRepository.save(newPakage);

        // when
        final List<Package> packageList = packageRepository.findByCreatedAtAfter(dateTime, PageRequest.of(0,1, Sort.by("packageSeq").descending()));

        // then
        assertEquals(1, packageList.size());
        assertEquals(newPakage.getPackageSeq(), packageList.get(0).getPackageSeq());
    }

    @Test
    public void updateCountAndPeriod() {
        // given
        Package packaze = new Package();
        packaze.setPackageName("3개월 바디프로필 완성 패키지");
        packaze.setPeriod(64);
        packageRepository.save(packaze);

        // when
        int updatedCount = packageRepository.updateCountAndPeriod(packaze.getPackageSeq(), 20, 44);
        Package updatePackaze = packageRepository.findById(packaze.getPackageSeq()).get();

        // then
        assertEquals(1, updatedCount);
        assertEquals(20, updatePackaze.getCount());
        assertEquals(44, updatePackaze.getPeriod());
    }

    @Test
    public void delete(){
        // given
        Package packaze = new Package();
        packaze.setPackageName("행사가 종료된 이용권");
        packaze.setCount(1);
        Package deletedPackaze = packageRepository.save(packaze);

        // when
        packageRepository.deleteById(deletedPackaze.getPackageSeq());

        // then
        assertTrue(packageRepository.findById(deletedPackaze.getPackageSeq()).isEmpty());
    }
}