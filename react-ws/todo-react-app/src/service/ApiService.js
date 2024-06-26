import { API_BASE_URL } from "../api-config";

// 책에서는 default가 없었지만 추가하지 ㅏ않으면 에러 발생
export function call(api, method, request) {
    let headers = new Headers({
        "Content-Type": "application/json",
    });

    //get access token and append authorization.
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    // if(accessToken === null || accessToken==="null") {
    //     window.location.href="/login";
    // }
    if(accessToken && accessToken !== null) {
        headers.append("Authorization", "Bearer " + accessToken)
    }

    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method,
    }

    if(request) {
        //GET
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options).then((response) => {
        console.log("response", response);
        // localStorage.setItem("res", response);
        if(response.status===200) {
            return response.json();
        } else if(response.status===403) {
            window.location.href="/login"; // redirect
        } else if(response.status===400) {
            alert("login failed");
            console.log(response);
        } else {
                // Promise.reject(response);
                throw Error(response);
        }
    }).catch((error) => {
        console.log("http error");
        console.log(error);
    });
}

export function signup(userDTO) {
    return call("/auth/signup", "POST", userDTO)
        .then( (response) => {
            console.log(response);
        });
}

export function signin(userDTO) {
    return call("/auth/signin", "POST", userDTO)
        .then( (response) => {
            console.log('response: ', response);
            localStorage.setItem("ACCESS_TOKEN", response.token);
            if(response!=undefined && response.hasOwnProperty("token")) {
                localStorage.setItem("ACCESS_TOKEN", response.token);
                window.location.href="/";
            }
        })
        .catch((error) => {
            console.log("error", error);
        });
}

export function socialLogin(provider) {
    const frontendUrl = window.location.protocol + "//" + window.location.host;
    window.location.href = API_BASE_URL + "/auth/authorize/" + provider + "?redirect_url=" + frontendUrl;
}

export function signout() {
    localStorage.setItem("ACCESS_TOKEN", null);
    window.location.href = "/login";
}