import React from 'react';
import NavBar from '../../components/NavBar'
import './Inquiry.less'
class Inquiry extends React.Component {
    constructor(props) {
        super(props)
        this.state = {

        }
    }
    componentDidMount() {
        let mySwiper = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: false,
            // 如果需要分页器
            pagination: '.swiper-pagination',
        });
    }
    render() {
        return <div>

            <NavBar title={'车险询价'}></NavBar>
            <div className="swiper-container" ref={self => this.swiperID = self}>
            <div className="swiper-wrapper banner-img">
                <div className="swiper-slide ">Slide 1</div>
                <div className="swiper-slide ">Slide 2</div>
            </div>
            <div className="swiper-pagination"></div>
        </div>
        </div>
    }
}

export default Inquiry

