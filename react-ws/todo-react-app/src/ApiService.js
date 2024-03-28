import { API_BASE_URL } from "./api-config";

// 책에서는 default가 없었지만 추가하지 ㅏ않으면 에러 발생
export function call(api, method, request) {
    let options = {
        headers: new Headers({
            "Content-Type": "application/json",
        }),
        url: API_BASE_URL + api,
        method: method,

    }

    if(request) {
        //GET
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options).then((response) => {
        if(response.status===200) {
            return response.json();
        }
    }).catch((error) => {
        console.log("http error");
        console.log(error);
    });
}