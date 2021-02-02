import React from 'react'
import {Link} from 'react-router-dom'
import { IoPersonCircle } from "react-icons/io5";
import logo from '../img/logo.png'

class SubHeader extends React.Component {
    //document.getElementById('info_img').addEventListener('click', () => this.props.history.push('/myInfo') )
    //document.getElementById('main_img').addEventListener('click', () => this.props.history.push('/main') )
    render(){
        return(
            <div className="container-fluid">
                <div className="row">
                <div className="col-4">
                <Link to='/main' className="title_wrapper">
                    <img className="" height="100" id="main_img" src={logo} href="./main"/>
                </Link>
                </div>
                <div className="col-6"></div>
                <div className="col-2">
                <Link to='/myInfo' className="mw-100">
                    <IoPersonCircle id="info_img" size="2.5em" className="mt-3 mx-3"></IoPersonCircle>
                </Link>
                </div>
                </div>

            </div>
        )
    }
}

export default SubHeader