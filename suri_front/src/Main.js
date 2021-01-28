import React, { Component } from 'react';
import Modal from 'react-modal'
import api from './API'
import Header from './Header'


class Main extends Component{
    render(){
        return(
            <div className="container-fluid">
                <Header />
                <div className="main_body row">
                    <nav id="sidebar" className="col-md-3 mx-3">
                        <div className="sidebar-header">
                            <h3>Filter</h3>
                        </div>
                        <div className="filter card">
                            <div className="filter_header card-header">
                                <button className="btn btn-link row mw-100" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseFilterSubject">
                                    <span className="col-5">과목</span>
                                </button>
                            </div>
                            <ul className="filter_list card-body filter_subject collapse show" id="collapseFilterSubject">
                                <li className="checkbox-wrap">
                                    <input type="checkbox" id="test1" data-filter="" data-filter-type=""></input>
                                    <label className="ml-3" for="test1"><span>test1</span></label>
                                </li>
                                <li className="checkbox-wrap">
                                    <input type="checkbox" id="test2" data-filter="" data-filter-type=""></input>
                                    <label className="ml-3" for="test2"><span>test2</span></label>
                                </li>
                                <li className="checkbox-wrap">
                                    <input type="checkbox" id="test3" data-filter="" data-filter-type=""></input>
                                    <label className="ml-3" for="test3"><span>test3</span></label>
                                </li>
                            </ul>
                        </div>
                        <div className="filter card">
                            <div className="filter_header card-header">
                                <button className="btn btn-link row mw-100" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseFilterSubject">
                                    <span className="col-5">문제 유형</span>
                                </button>
                            </div>
                            <ul className="filter_list card-body filter_subject collapse show" id="collapseFilterSubject">
                                <li className="checkbox-wrap">
                                    <input type="checkbox" id="test1" data-filter="" data-filter-type=""></input>
                                    <label className="ml-3" for="test1"><span>기출</span></label>
                                </li>
                                <li className="checkbox-wrap">
                                    <input type="checkbox" id="test2" data-filter="" data-filter-type=""></input>
                                    <label className="ml-3" for="test2"><span>기출 X</span></label>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <div className="col-md-8" id="content">
                        <nav className="navbar navbar-expand-lg navbar-light bg-light">
                            <div className="container-fluid">
                                <span>-------- Nav bar ---------</span>
                            </div>
                        </nav>

                        <div className="my-10"> 
                            <ul>--------- problem list --------- </ul>
                        </div>
                    </div>

                </div>
            </div>
        )
    }

}

export default Main