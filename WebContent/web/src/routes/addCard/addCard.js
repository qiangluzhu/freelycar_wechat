import React from 'react'
import ReactDOM from 'react-dom';
import { Flex } from 'antd-mobile'
import './addCard.less'
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
            card: [
                { name: '次卡', src: times_card },
                { name: '金卡', src: gold_card },
                { name: '白金卡', src: wgold_card },
                { name: '钻石卡', src: diamonds_card },
                { name: '至尊卡', src: extreme_card }
            ],
            arrowIndex: -1
        }
    }
    componentDidMount() {
        let mySwiper2 = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: false,
            slidesPerView: 4,
            slidesPerGroup: 1,
            centeredSlides: false,
            // 如果需要分页器
        });
    }

    onHanleArrowDisplay = (index) => {
        this.setState({
            arrowIndex:index
        });
    }
    render() {

        let cards = this.state.card;

        let cardList = cards.map((item, index) => {
            return <div key={index} className="card-item swiper-slide" onClick={() => { this.onHanleArrowDisplay(index) }}>
                <img src={item.src} />
                <div className='label'>{item.name}</div>
                <div className={`arrow ${this.state.arrowIndex==index?'':'hidden'}`}></div>
            </div>

        })

        return (<div>
            <NavBar title={'会员卡添加'}></NavBar>
            <div className="swiper-container all-card-list" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    {cardList}
                </div>
            </div>

            <div className='vip-service'>
                <div className='vip-service-div'>
                    <div className='vip-service-header'>会员专项</div>

                    <div className='vip-service-item'>
                        <div className='label-left'>洗车</div>
                        <div className='label-right'>
                            <span className='count'>25</span>
                            <span className='unit'>次</span>
                        </div>
                    </div>
                    <div className='vip-service-item'>
                        <div className='label-left'>洗车</div>
                        <div className='label-right'>
                            <span className='count'>25</span>
                            <span className='unit'>次</span>
                        </div>
                    </div>
                    <div className='vip-service-item'>
                        <div className='label-left'>洗车</div>
                        <div className='label-right'>
                            <span className='count'>25</span>
                            <span className='unit'>次</span>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        );
    }
}

export default OrderDetail
