
import React,{Component} from 'react'
import Login from './page/Login'
import Main from './page/Main'
import MyInfo from './page/MyInfo'
import makePB from './page/makePB'
import Problem from './page/Problem'
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from 'react-router-dom'

class App extends Component{
  render(){
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/main" component={Main}/>
          <Route exact path="/myInfo" component={MyInfo}/>
          <Route exact path="/makePB" component={makePB}/>
          <Route exact path="/problem" component={Problem}/>          
        </Switch>
      </Router>  
    )
  }
}

export default App
