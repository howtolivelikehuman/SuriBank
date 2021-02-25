import React from 'react'
import {Link} from 'react-router-dom'
import { IoPersonCircle } from "react-icons/io5";
import logo from '../img/logo2.png'

class Header extends React.Component {
    //document.getElementById('info_img').addEventListener('click', () => this.props.history.push('/myInfo') )
    //document.getElementById('main_img').addEventListener('click', () => this.props.history.push('/main') )
    render(){
        return(
            <div className="container">
                <div className="row my-3">
                <img className="mx-auto" height="100" id="main_img" src={logo}/>
                </div>
            </div>
        )
    }
}

export default Header