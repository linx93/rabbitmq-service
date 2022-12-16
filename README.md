# rabbitmq 服务发送消息



```ison
POST http://192.168.1.111:4636/api/producer/send
Content-Type: application/json

{
  "baseMqInfo":{
    "content":"test",
    "exchange":"hello",
    "queue":"world",
    "exchangeType": "direct",
    "routingKey":"1"
  },
  "dtid":"dtid",
  "machineId":"uuid",
  "title":"title"
}
```

## Body参数说明

```json
{
  "baseMqInfo":{
    "content":"test", //消息体 不能为空
    "exchange":"hello", //交换机名称 不能为空
    "queue":"world", // 队列名称 不能为空
    "exchangeType": "direct", //交换机类型 direct ，fanout 不能为空
    "routingKey":"1" // 路由key 不能为空
  },
  "dtid":"dtid", // 数字身份 
  "machineId":"uuid", // 机器码 不能为空
  "title":"title" // 标题 
}
```



## 响应参数

```json
{
  "code": "200000",
  "message": "操作成功",
  "payload": null
}
```

