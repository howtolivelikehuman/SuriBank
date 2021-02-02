import React from 'react'
import {Link} from 'react-router-dom'
import { IoPersonCircle } from "react-icons/io5";
import logo from '../img/logo.png'

class Header extends React.Component {
    //document.getElementById('info_img').addEventListener('click', () => this.props.history.push('/myInfo') )
    //document.getElementById('main_img').addEventListener('click', () => this.props.history.push('/main') )
    render(){
        return(
            <div className="container-fluid">
                <Link to='/myInfo' className="d-flex flex-row-reverse">
                    <IoPersonCircle id="info_img" className="mt-3 col-md-2"></IoPersonCircle>
                </Link>
                <Link to='/main' className="title_wrapper row">
                    <img className="w-100 mx-auto" id="main_img" src={logo} href="./main"/>
                </Link>

            </div>
        )
    }
}

export default Header