import React from 'react';
import Swiper from 'swiper';
import NavBar from '../../components/NavBar'
import './Inquiry.less'
class Inquiry extends React.Component {
    constructor(props) {
        super(props)
        this.state = {

        }
    }
    componentDidMount() {
        new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: true,
            
            // 如果需要分页器
            pagination: '.swiper-pagination',
        });
    }
    render() {
        return <div>
            <NavBar title={'车险询价'}></NavBar>
            <div className="swiper-container"  ref={self => this.swiperID = self} style={{width:'100%'}} >
                <div className="swiper-wrapper">
                    <div className="swiper-slide">Slide 1</div>
                    <div className="swiper-slide">Slide 2</div>
                    <div className="swiper-slide">Slide 3</div>
                </div>
                <div className="swiper-pagination"></div>
            </div>
        </div>
    }
}

export default Inquiry

