import {Link} from "react-router-dom";
// import "../../styles/_topnav.scss";

import React, {useState} from "react";

export const TopNav = () => {
    return (
    <div className="fixed w-full h-16 bg-gradient-to-r from-purple-400 via-pink-500 to-red-500 text-white justify-between flex flex-row items-center">
        <div className="brand-logo text-2xl font-bold px-5">Smart Kindergarten &trade;</div>
        <ul className="menu-list flex flex-row px-5">
            <li className="menu-list-item px-5"><a href="#">Home</a></li>
            <li className="menu-list-item px-5"><a href="#">History</a></li>
        </ul>
    </div>
        );
}
