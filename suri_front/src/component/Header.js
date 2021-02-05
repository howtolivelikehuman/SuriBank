import React from 'react'
import {Link} from 'react-router-dom'
import { IoPersonCircle } from "react-icons/io5";
import logo from '../img/logo.png'

class Header extends React.Component {
    //document.getElementById('info_img').addEventListener('click', () => this.props.history.push('/myInfo') )
    //document.getElementById('main_img').addEventListener('click', () => this.props.history.push('/main') )
    render(){
        return(
            <div className="container-fluid mt-10">
                <img className="w-100 mx-auto" id="main_img" src={logo}/>
            </div>
        )
    }
}

export default Header