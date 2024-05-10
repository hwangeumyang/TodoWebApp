import React from "react";
import { Navigate } from "react-router-dom";

const SocialLogin = (props) => {
    const getUrlParamter = (name) => {
        let search = window.location.search;
        let params = new URLSearchParams(search);
        return params.get(name);
    };

    const token = getUrlParamter("token");

    console.log("토큰 파싱: " + token);

    if(token) {
        console.log("save token in local storage: " + token);
        localStorage.setItem("ACCESS_TOKEN", token);

        return (
            <Navigate
                to= {{
                    pathname: "/",
                    state: { from: props.location },
                }}
                />
        );
    } else {
        return (
            <Navigate
                to = {{
                    pathname: "/login",
                    state: { from: props.location },
                }}
                />
        );
    }

};

export default SocialLogin;