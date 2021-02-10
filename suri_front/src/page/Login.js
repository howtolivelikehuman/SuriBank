import React, { Component } from 'react';
import Modal from 'react-modal'
import Header from '../component/Header';
import '../css/login.sass'
import api from '../util/API'

class Login extends Component{
    state={
        modalOpen:false,   
        password:"",
        check_password:"",
        name:"",
        nickname:"",
        major:"컴퓨터과학부",
        email:"",
        Login_id:"",
        Login_pw:"",
        valid_email:false,
        same_password:false,
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
        if(name=="check_password") this.check_password()
    }
    signup_handler=()=>{
        
        if(this.state.valid_email == false){
            alert("이메일 중복 체크를 해주세요")
            return null
        }

        // if(this.state.same_password == false){
        //     alert("비밀번호를 확인해주세요")
        //     return null
        // }

        console.log(this.state.email, this.state.password, this.state.name, this.state.major, this.state.nickname)
        fetch('http://localhost:8081/api/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(
                {
                    
                        email:this.state.email,
                        password:this.state.password,
                        name:this.state.name,
                        major:this.state.major,
                        nickname:this.state.nickname,
                    
                }
            )
        })
        .then(res=>{
            //console.log(res)

            if(res.status==200)
                alert("회원가입이 완료되었습니다!")
            else alert("회원가입 실패")
            this.close_signup_modal()
        })
    }
    check_password = () => {
        if(this.state.password==this.state.check_password){
            this.setState({same_password:true}) 
        }
        else this.setState({same_password:false})
    }
    check_email_overlap = () => {
        const email = this.state.email
        api.post('/user/checkId', {
            id: email
        })
        .then(res => {
            if(res.status=="200") {
                alert("사용 가능한 이메일 입니다.")
                this.setState({valid_email:true})
            }
        })
        .catch(()=>{
            //console.log(JSON.stringify(err))

            // if(err.response){
            //     if(err.response.status==409) alert("이미 존재하는 이메일 입니다.")
            //     else alert('오류가 발생하였습니다. 다시 시도해주세요')
            // }
            this.setState({valid_email:false})
        })
        //TO DO: err 뜨면 무조건 409라고 간주, 상태 코드 수정할지, 코드상으로 변경할지..?
    }

    login_click_handler=()=>{

        //this.props.history.push('/main')
        //this.props.history.push('/main_manager')

        const email = document.getElementsByName("Login_id")[0].value;
        const password = document.getElementsByName("Login_pw")[0].value;
        //console.log(id, password)
        fetch('http://localhost:8081/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(
                { 
                        email: email,
                        password:password
                    
                }
            )
        })
        .then(res=>{
            console.log(res)

            if(res.status == 200){
                alert("로그인 성공!")
                
                //link to main
                //api.get(`/user`)
                this.props.history.push('/main')
                
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
        var message_pw = ""
        if(this.state.password!=this.state.check_password)
            message_pw = 
                <div className="row mb-3">
                    <div className="mx-auto">
                        <small color="red">비밀번호가 일치하지 않습니다.</small>
                    </div>
                </div>

        return(
            <div className="container">            
                <Header/>
                <div className="row">
                    <div className="col-6 card login_wrapper m-auto"> 
                        <div className="my-3">
                            <h4 className="card-title text-center mb-1">Login</h4><hr></hr>   
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

                        <Modal  className="bg-light modal-dialog modal-md" ariaHideApp={false} isOpen={this.state.modalOpen} onRequestClose={()=>this.close_signup_modal()}>
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
                                            <label className="col-sm-4">이메일</label>
                                            <input name="email" className="form-control col-sm-6 mx-3  mod_input input_email" type="email" placeholder="email" onChange={this.input_handler}/>
                                            <button className="btn btn-secondary col-sm-1" onClick={()=>this.check_email_overlap()}> &#10003;</button>
                                        </div>
                                        <div className="row my-3">
                                            <label className="col-sm-4">비밀번호</label>
                                            <input name="password" className="form-control col-sm-7 mx-3 mod_input input_password" type="password" placeholder="password" onChange={this.input_handler}/>   
                                        </div>                                       
                                         <div className="row mt-3">
                                            <label className="col-sm-4">비밀번호 확인</label>
                                            <input name="check_password" className="form-control col-sm-7 mx-3 mod_input input_password" type="password" placeholder="password" onChange={this.input_handler}/>   
                                        </div>
                                        {message_pw}

                                        <div className="row my-3">   
                                            <label className="col-sm-4">이름</label>
                                            <input name="name" className="form-control col-sm-7 mx-3 mod_input input_name" type="text" placeholder="name" onChange={this.input_handler}/>
                                        </div> 
                                        <div className="row my-3">
                                            <label className="col-sm-4">닉네임</label>
                                            <input name="nickname" className="form-control col-sm-7 mx-3  mod_input input_nickname" type="text" placeholder="nickname" onChange={this.input_handler}/>
                                        </div>
                                        <div className="row my-3">
                                            <label className="col-sm-4">전공</label>
                                            <select className="form-control col-sm-7 mx-3" labelId="simple-select-label" name="major" onChange={this.input_handler}>
                                                <option value={"컴퓨터과학부"}>컴퓨터과학부</option>
                                                <option value={"경영학부"}>경영학부</option>
                                                <option value={"화학공학과"}>화학공학과</option>
                                                <option value={"철학과"}>철학과</option>
                                            </select>
                                        </div>

                                    </div>
                                    <div className="modal-footer">
                                        <button className="btn btn-secondary"onClick={()=>this.signup_handler()}>sign up</button>
                                    </div>
                                </div>
                            </div>
                        </Modal>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login