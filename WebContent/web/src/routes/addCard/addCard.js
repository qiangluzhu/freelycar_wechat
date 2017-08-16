import React from 'react';
import { Flex } from 'antd-mobile'
import './addCard.less'
import Swiper from 'react-swipe';
import NavBar from '../../components/NavBar'
import gold_card from '../../img/gold_card.png'
import wgold_card from '../../img/wgold_card.png'
import diamonds_card from '../../img/diamonds_card.png'
import extreme_card from '../../img/extreme_card.png'
import times_card from '../../img/times_card.png'
class OrderDetail extends React.Component {

    constructor(props) {
        super(props)
        this.state = {

        }
    }

    render() {
        return <div className="body-bac">
            <NavBar title="订单详情" />

            <Swiper className="all-card-list carousel" swipeOptions={{continuous: false}}>
                    <div className="card-item">
                        <img src={times_card} />
                        <div className='label'>次卡</div>
                        <div className='arrow'></div>
                    </div>
                    <div className="card-item">
                        <img src={gold_card} />
                        <div className='label'>金卡</div>
                        <div className='arrow'></div>
                    </div>
                    <div className="card-item">
                        <img src={wgold_card} />
                        <div className='label'>白金卡</div>
                        <div className='arrow'></div>
                    </div>
                    <div className="card-item">
                        <img src={diamonds_card} />
                        <div className='label'>钻石卡</div>
                        <div className='arrow'></div>
                    </div>
                    <div className="card-item">
                        <img src={extreme_card} />
                        <div className='label'>会员至尊卡</div>
                        <div className='arrow'></div>
                    </div>

            </Swiper>

        </div>
    }
}

export default OrderDetail