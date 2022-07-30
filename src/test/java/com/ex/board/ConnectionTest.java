package com.ex.board;

import com.ex.board.config.MybatisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ConnectionTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(MybatisConfig.class);

    @Autowired
    private DataSource dataSource;

    @Test
    void sessionFactoryTest() {
        Object sessionFactory = ac.getBean("sqlSessionFactory");
        assertThat(sessionFactory).isNotNull();
    }

    @Test
    void sessionTemplateTest() {
        Object sessionFactory = ac.getBean("sessionTemplate");
        assertThat(sessionFactory).isNotNull();
    }

    @Test
    void connectionTest() {
        try {
            Connection con = dataSource.getConnection();
            if (con != null) {
                System.out.println("연결 성공 = " + con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
