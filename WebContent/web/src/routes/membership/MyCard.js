import React from 'react';
import NavBar from '../../components/NavBar'
import './MyCard.less'
import cika from '../../img/cika.png'
import zhizun from '../../img/zhizun.png'
import jinka from '../../img/jinka.png'
import {getCardList } from '../../services/service.js'
import {browserHistory} from 'dva/router'
class MyCard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            cards: [{ name: '会员次卡', number: '46516514465', time: '2017.05.23', id: '1' },
            { name: '会员至尊卡', number: '46516514465', time: '2017.05.23', id: '2' }, {
                name: '会员金卡', number: '46516514465', time: '2017.05.23', id: '3'
            }]
        }
    }

    componentDidMount(){
        let myInit = {
            method: 'get',
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type':'application/x-www-form-urlencoded;charset=utf-8' 
            }
        }

        getCardList({
            page:1,
			number:10
        },myInit).then((res)=>{
            console.log(res)
        }).catch((error)=>{
            console.log(error)
        })
    }

    render() {
        let card = this.state.cards.map((item, index) => {
            let background
            switch (item.id) {
                case '1': background = cika; break;
                case '2': background = zhizun; break;
                case '3': background = jinka; break;
            }
            return <div key={index} className="membership-mycard" style={{ background: `url(${background})`,backgroundSize:'100% 100%' }}>
                <div className="card-name">{item.name}</div>
                <div className="card-number">{item.number}</div>
                <div className="card-time">{item.time}</div>
            </div>
        })
        return <div>
            <div className="nav-bar-title" style={{marginBottom:'.3rem'}}>
                <i className="back" onClick={() => { history.back() }}></i>
                我的会员卡
                <span className="scan" onClick={()=>{browserHistory.push('/addCard')}}>添加</span>
            </div>
            {card}
        </div>
    }
}
export default MyCard