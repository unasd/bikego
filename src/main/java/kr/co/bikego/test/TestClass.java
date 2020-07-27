package kr.co.bikego.test;

import kr.co.bikego.domain.code.AsStat;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.repository.AttachRepository;
import kr.co.bikego.test.domain.repository.CrudRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestClass {

    public static void main(String[] args) throws Exception {
        System.out.println(AsStat.A.getStatName());
    }

//    @Autowired
//    private AttachRepository attachRepository;
//
    @Autowired
    private AttachRepository attachRepository;

    @Autowired
    private CrudRepository crudService;

//    @Test
//    public void attach_select_test(){
//        final AttachId attachId = new AttachId();
//        List<AttachEntity> attachEntities = attachRepository.findAllById((Iterable<AttachId>) attachId);
//        assertNotNull(attachEntities);
//    }

    @Test
    public void attach_select_all() {
        List<AttachEntity> attachDtoList = attachRepository.findAll();
        System.out.println(attachDtoList);
    }

    @Test
    public void generateAttachId(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String type = "as";
        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));;
        System.out.println(time + "_" + type + "_"  + randomValue);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void insertTest(){
//        LocalDateTime localDateTime = LocalDateTime.now();
//        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
//        String type = "as";
//        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));;
//
//        AttachId attachId = new AttachId();
//        attachId.setAttach_id(time + "_" + type + "_"  + randomValue);
//        attachId.setAttach_id("20200425170878_as_82081");
//
//        AttachDto attachDto = new AttachDto();
//        attachDto.setIdAttach("20200501085688_crud_35928");
//        attachDto.setNmOrgFileAttach("test");
//        attachDto.setNmSrvFileAttach("test");
//        attachDto.setPathFileAttach("test");
//        attachDto.setSizeFileAttach((long) 5000);
//        attachDto.setExtendsAttach("jpg");
//        attachDto.setRegisterAttach("tester");
//        attachDto.setRegdtAttach(LocalDateTime.now());
//        attachDto.setYnDel("N");

//        List<AttachEntity> attachId2 = attachRepository.findByAttachId("20200501085688_crud_35928");

        AttachEntity attachEntity = attachRepository.findByIdAttachAndSnFileAttach("20200501085688_crud_35928", 1);

        System.out.println(attachEntity);
    }

    @Test
    public void lastRecordTest(){
        AttachEntity attachEntity = attachRepository.findTopByIdAttachOrderBySnFileAttachDesc("20200501085688_crud_35928");
        System.out.println(attachEntity.getSnFileAttach());
    }

}
