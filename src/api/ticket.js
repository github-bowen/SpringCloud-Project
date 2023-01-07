import {request} from "@/api/api";


export async function addTicketReq(method, params, trainId) {
    // 买票 指定车次ID
    const url = `/addTicket/${trainId}`;
    return request(url, method, params);
}

export async function delTicketReq(method, params, ticketId) {
    // 退票 指定票ID
    const url = `/delTicket/${ticketId}`;
    return request(url, method, params);
}

export async function ticketDetailReq(method, params, ticketId) {
    //车票信息
    const url = `/ticketDetail/${ticketId}`;
    return request(url, method, params);
}

