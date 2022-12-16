package net.phadata.rabbitmq.model;

/**
 * @author tanwei
 * @desc
 * @time 1/26/21 4:14 PM
 * @since 1.0.0
 */
public enum ErrorCode {
    /**
     * 项目错误编码
     */
    SUCCESS("200000", "操作成功"),
    TYPE_ERROR("400000", "暂不支持此解析类型"),
    DTID_DEACTIVATED("400001", "数字身份已注销"),
    DTID_INVALID("400002", "数字身份无效"),
    METHOD_NOT_SUPPORTED("400003", "暂不支持数字身份方法"),
    DTID_NOT_FOUND("400004", "数字身份不存在"),
    DTID_SIGN_ERROR("400005", "数字身份签名错误"),
    DTID_SERVICE_INVALID("400006", "暂未提供此服务"),
    PARAMS_ERROR("400007", "参数错误"),
    URI_NOT_FOUND("400404","URI不能存在"),
    PROOF_ERROR("400008", "凭证无效"),
    TOKEN_ERROR("500001", "用户令牌错误或已失效"),
    DATABASE_ERROR("500002", "数据库操作异常"),
    USER_PASS_ERROR("900001", "用户名或密码错误"),
    USER_NOT_EXIST("900002", "用户不存在"),
    REQUEST_SIGN_ERROR("900003", "应用请求签名无效"),
    APP_STATE_ERROR("900004", "应用正在审核中"),
    APP_INVALID("900005", "应用不存在"),
    LICENSE_INVALID("900006", "license非法"),
    LICENSE_EXPIRED("900007", "license已过期"),
    LICENSE_OWNER_ERROR("900008", "license签发方无效"),
    ISSUER_STATE_ERROR("900009", "注册状态正在审核中"),
    ISSUER_INVALID("900010", "此注册ID无效"),
    ERROR("-1", "系统繁忙，请稍后重试"),
    ENTITY_NOT_FOUND("404000", "没有找到该对象"),
    METHOD_NOT_SUPPORT("404001", "请求方式不正确"),
    /**
     * 参数异常
     */
    PARAMETER_EXCEPTION("400101", "参数错误"),
    PERMISSIONDENIED_EXCEPTION("406000", "权限错误"),
    FAILED("500000", "内部服务错误"),
    IP_ERROR("500003", "请求服务IP无权限"),
    GRID_ERROR("500011", "远程TDR已断开连接"),
    PARAMS_SIGN_ERROR("600001", "参数签名校验错误"),
    BSN_EXPIRE("600002", "业务流水号已过期"),
    BSN_INVALID("600003", "业务流水号无效"),
    NOT_SUPPORT("600004", "暂不支持此类型接入"),
    CID_INVALID("600005", "凭证不存在"),
    CLAIM_ERROR("600006", "凭证内容无效"),
    PIECES_ERROR("60007", "创建凭证张数与提供信息不匹配"),
    NOT_GRANTED("60008", "创建凭证张数与提供信息不匹配");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
