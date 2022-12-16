package net.phadata.rabbitmq;

import com.alibaba.fastjson.JSON;
import net.phadata.rabbitmq.model.BizMessage;
import net.phadata.rabbitmq.model.RabbitBinding;
import net.phadata.rabbitmq.service.MessageService;
import net.phadata.rabbitmq.service.RabbitManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqServiceImplApplicationTests {

    @Autowired
    private MessageService messageService;

    @Test
    void testMq() {


        BizMessage.BaseMqInfo baseMqInfo = new BizMessage.BaseMqInfo()
                .setContent("test")
                .setExchange("test_7_1")
                .setQueue("test1")
                .setRoutingKey("test_7_1");

        BizMessage bizMessage = new BizMessage()
                .setTitle("title")
                .setDtid("dtid")
                .setMachineId("uuid")
                .setBaseMqInfo(baseMqInfo);

        System.out.println(JSON.toJSONString(bizMessage, true));

        // messageService.createMessage(bizMessage);

    }

    @Autowired
    RabbitManagementService rabbitManagementService;


    @Test
    void bindTest() {

        RabbitBinding rabbitBinding = new RabbitBinding();
        rabbitBinding.setRabbitQueue(7);
        rabbitBinding.setRabbitExchange(7);
        rabbitBinding.setRoutingKey("tsssss");
        rabbitManagementService.createBinding(rabbitBinding);


    }

    @Test
    void test() {
        System.out.println(rabbitManagementService.findAllExchange());
    }


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void quequs() {
//        System.out.println(rabbitTemplate.expectedQueueNames());
        System.out.println(rabbitTemplate.getExchange());
        System.out.println(rabbitTemplate.getUUID());
        String uid = "8cc3e2a8-4718-4d16-8e01-eac3d1f188d1";
    }

}
