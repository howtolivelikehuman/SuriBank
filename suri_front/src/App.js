
import React,{Component} from 'react'
import Login from './Login'
import Main from './Main'
import MyInfo from './MyInfo'
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
        </Switch>
      </Router>  
    )
  }
}

export default App
