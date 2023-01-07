import {request} from "@/api/api";

export async function registerReq(method, params) {
    //注册
    const url = `/register`;
    return request(url, method, params);
}

export async function loginReq(method, params) {
    //登录
    const url = `/login`;
    return request(url, method, params);
}

export async function userInfoReq(method, params) {
    //个人信息
    const url = `/userInfo`; // userId在token
    return request(url, method, params);
}


export async function userTicketsReq(method, params, userId) {
    //查询指定用户的车票
    const url = `/userTickets/${userId}`;
    return request(url, method, params);
}

