import React from 'react';
import NavBar from '../../components/NavBar'
import './MyCard.less'
import cika from '../../img/cika.png'
import zhizun from '../../img/zhizun.png'
import jinka from '../../img/jinka.png'
import baijin from '../../img/baijin.png'
import zuanshi from '../../img/zuanshi.png'
import { myCard } from '../../services/user.js'
import { browserHistory } from 'dva/router'
class MyCard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            cards: []
        }
    }

    componentDidMount() {
        myCard({
            clientId: '11'
        }).then((res) => {
            console.log(res)
            this.setState({
                cards: res.data.data
            })
        }).catch((error) => {
            console.log(error)
        })
    }

    render() {
        let card = this.state.cards.map((item, index) => {
            let background
            switch (item.service.name) {
                case '洗车次卡': background = cika; break;
                case '至尊卡': background = zhizun; break;
                case '金卡': background = jinka; break;
                case '白金卡': background = baijin; break;
                case '钻石卡': background = zuanshi; break;
            }
            return <div key={index} className="membership-mycard" onClick={() => { browserHistory.push(`/membership/mycard/detail/${item.id}`) }} style={{ background: `url(${background})`, backgroundSize: '100% 100%' }}>
                <div className="card-name">{item.service.name}</div>
                <div className="card-number">{item.cardNumber}</div>
                <div className="card-time">{item.service.createDate.slice(0, 10)}</div>
            </div>
        })
        return <div className="body-bac">
            <div className="nav-bar-title" style={{ marginBottom: '.3rem' }}>
                <i className="back" onClick={() => { history.back() }}></i>
                我的会员卡
                <span className="scan" onClick={() => { browserHistory.push('/addCard') }}>添加</span>
            </div>
            {card}
        </div>
    }
}
export default MyCard