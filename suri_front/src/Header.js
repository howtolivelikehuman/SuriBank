import React from 'react'
import { IoPersonCircle } from "react-icons/io5";
import logo from './img/logo.png'

const Header = () => {
    return(
        <div className="container-fluid">
            <div className="d-flex flex-row-reverse">
                <IoPersonCircle className="mt-3 col-md-2"></IoPersonCircle>
            </div>
            <div className="title_wrapper row">
                <img className="w-100 mx-auto" src={logo}/>
            </div>

        </div>
    )
}

export default Header