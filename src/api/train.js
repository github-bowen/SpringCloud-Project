import {request} from "@/api/api";

export async function showTrainReq(method, params) {
    // 展示所有车次
    const url = `/allTrain`;
    return request(url, method, params);
}

export async function addTrainReq(method, params) {
    // 添加车次
    const url = `/addTrain`;
    return request(url, method, params);
}

export async function delTrainReq(method, params, trainId) {
    // 删除车次
    const url = `/delTrain/${trainId}`;
    return request(url, method, params);
}

export async function changeTrainReq(method, params, trainId) {
    //修改车次
    const url = `/changeTrain/${trainId}`;
    return request(url, method, params);
}

export async function trainDetailReq(method, params, trainId) {
    //车次信息
    const url = `/trainDetail/${trainId}`;
    return request(url, method, params);
}
