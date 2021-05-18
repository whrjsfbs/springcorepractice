package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("singletonBean1 = " + prototypeBean1);
        System.out.println("singletonBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

//        prototypeBean1.destroy();
//        prototypeBean2.destroy();
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            //prototype이라 만들고 바로 container는 버리기 때문에 ApplicationContext를 close()를 진행해도 이게 실행되지 않음
            //이제 이 prototype 객체를 관리하는 건 이 객체를 받은 녀석이 해야함
            System.out.println("PrototypeBean.destroy");
        }
    }
}
