import React, { Component } from 'react';
import Modal from 'react-modal'
import api from './API'



class Login extends Component{
    state={
        modalOpen:false,   
        id:"",
        password:"",
        name:"",
        nickname:"",
        major:"",
        email:"",
        Login_id:"",
        Login_pw:"",
    }
    componentDidMount() {
        document.querySelector('.loginPw').addEventListener("keyup", e => {
            if (e.key === 'Enter' || e.keyCode === 13) {
                document.querySelector('.loginBtn').click();
            }
        })

    }
    input_handler=(e)=>{
        const{name, value}=e.target
        this.setState({[name]:value})
    }
    signup_handler=()=>{    
        fetch('http://localhost/api/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(
                {
                    result_code: "200",
                    description: "OK",
                    data: 
                        {
                            email:this.state.email,
                            id:this.state.id,
                            password:this.state.password,
                            name:this.state.name,
                            major:this.state.major,
                            nickname:this.state.nickname,
                        
                        }
                }
            )
        })
        .then(res => res.json())
        .then(res=>{
            console.log(res)
            if(res.message=="OK")
            alert("회원가입이 완료되었습니다!")
            else alert("회원가입 실패")
            this.close_signup_modal()
        })
    }
    login_click_handler=()=>{
        this.props.history.push('/main')
        //this.props.history.push('/main_manager')

        const id = document.getElementsByName("Login_id")[0].value;
        const password = document.getElementsByName("Login_pw")[0].value;
        console.log(id, password)
        fetch('http://localhost/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(
                {
                    id: id,
                    password:password
                }
            )
        })
        .then(res => res.json())
        .then(res=>{
            console.log(res)
            if(res.message == "OK"){
                alert("로그인 성공!")
                
                //link to main
                api.get(`/user`)
                .then(
                    this.props.history.push('/main')
                )
            }
            else{
                alert("아이디/비밀번호를 확인해주세요")
                this.props.history.push('/')
            }
        })
    }

    open_signup_modal=()=>{
        this.setState({modalOpen:true})
    }    
    close_signup_modal=()=>{
        this.setState({modalOpen:false})
    }
    render(){
        return(
         
            <div className="container">            
                <div className="header mb-1000">
                    <h1 className="title">
                        Suri Bank
                    </h1>
                </div>
                
                <div className="card login_wrapper m-10"> 
                    <article className="card-body row justify-content-center my-auto col-lg-2">
                        <div className="col-lg-10 col-12 my-3">
                            <h4 className="card-title text-center mb-1">Log in</h4><hr></hr>   
                            <div className="form-group">
                                <label className="form-control-label text-muted">Username</label>
                                <input name="Login_id" className="loginId input_e form-control" type="text" placeholder="ID" />
                            
                            </div>
                            <div className="form-group">
                                <label className="form-control-label text-muted">Password</label>
                                <input name="Login_pw" className="loginPw form-control input_e" type="password" placeholder="password"/>
                            </div>
                            <div className="row justify-content-center my-3 px-3"> 
                                <button className="loginBtn btn btn-secondary btn-lg btn-block" onClick={()=>this.login_click_handler()}>Login</button>
                            </div>
                            <div className="row justify-content-center my-3 px-3"> 
                                <button className="signupBtn btn btn-secondary btn-lg btn-block" onClick={()=>this.open_signup_modal()}>Sign In</button>
                            </div>
                        </div>

                        <Modal  className="bg-light modal-dialog modal-sm" ariaHideApp={false} isOpen={this.state.modalOpen} onRequestClose={()=>this.close_signup_modal()}>
                            <div>
                                <div className="modal-content bg-light signupModal">
                                <div className="modal-header">
                                    <h4 className="modal-title">회원가입</h4>
                                    <span className="close" onClick={()=>this.close_signup_modal()}>
                                    &times;
                                    </span>
                                    </div>
                                    <div className="modal-body" style={{'max-height': 'calc(100vh - 210px)', 'overflow-y': 'auto'}} onClick={()=>this.state.modalOpen}>
                                        <div className="row my-3">         
                                            <label className="col-sm-5">아이디</label>
                                            <input name="id" className="form-control mod_input input_id mx-3" type="text" placeholder="ID" onChange={this.input_handler}/>    
                                        </div>
                                        <div className="row my-3">
                                            <label className="col-sm-5">비밀번호</label>
                                            <input name="password" className="form-control mx-3 mod_input input_password" type="password" placeholder="password" onChange={this.input_handler}/>   
                                        </div>
                                        <div className="row my-3">   
                                            <label className="col-sm-5">이름</label>
                                            <input name="name" className="form-control mx-3 mod_input input_name" type="text" placeholder="name" onChange={this.input_handler}/>
                                        </div> 
                                        <div className="row my-3">
                                            <label className="col-sm-5">닉네임</label>
                                            <input name="nickname" className="form-control mx-3  mod_input input_nickname" type="text" placeholder="nickname" onChange={this.input_handler}/>
                                        </div>
                                        <div className="row my-3">
                                            <label className="col-sm-5">전공</label>
                                            <select labelId="simple-select-label" name="major" onChange={this.input_handler}>
                                                <option value={"컴퓨터과학부"}>컴퓨터과학부</option>
                                                <option value={"경영학부"}>경영학부</option>
                                                <option value={"화학공학과"}>화학공학과</option>
                                                <option value={"철학과"}>철학과</option>
                                            </select>
                                        </div>
                                        <div className="row my-3">
                                            <label className="col-sm-5">이메일</label>
                                            <input name="email" className="form-control mx-3  mod_input input_email" type="email" placeholder="email" onChange={this.input_handler}/>
                                        </div>
                    

                                    </div>
                                <div className="modal-footer">
                                    <button className="btn btn-secondary"onClick={()=>this.signup_handler()}>sign up</button>
                                </div>
                                    </div>

                            </div>
                        </Modal>
                    </article>
                </div>
            </div>
        )
    }
}

export default Login