package com.example.roomrentalapplication.ui.search_main

import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import com.example.roomrentalapplication.ui.search_main.adapter.ItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchMainViewModel @Inject constructor() : BaseViewModel() {

    val data = MutableStateFlow(
        listOf(
            ItemData(
                "https://roomlessrent.com/assets/imgs/home/luxury_houses.jpg",
                "Luxury Houses",
                " Details and finishes of excellence, unique properties complete with every service. ",
                1
            ),
            ItemData(
                "https://roomlessrent.com/assets/imgs/home/premium_houses.jpg",
                "Premium Houses",
                " High-level, recently renovated properties in central locations. Perfect solutions for enjoying every city. ",
                2
            ),
            ItemData(
                "https://roomlessrent.com/assets/imgs/home/business_stays.jpg",
                "Business Stays",
                " The best properties for your working stays. Spaces designed to make smart working as easy as possible with personal workspaces isolated from the rest of the house. ",
                3
            ),
            ItemData(
                "https://roomlessrent.com/assets/imgs/home/family_houses.jpg",
                "Family houses",
                " Large size, family-friendly spaces, facilities for children. Located in residential areas, convenient for travelling and reaching the main infrastructures. ",
                4
            ),
            ItemData(
                "https://roomlessrent.com/assets/imgs/home/student_housing.jpg",
                "Selected Student Housing",
                " Excellent flats and rooms for students, located in the main university areas, single or shared only with peers. Perfect properties to live the university experience and not think about the rest. ",
                5
            )
        )
    )
    val dataCities = MutableStateFlow(
        listOf(
            ItemData(
                "https://image.thanhnien.vn/w1024/Uploaded/2022/szyrbfly-bn/2021_11_13/da-nang-5242.jpg",
                "Da Nang"
            ),
            ItemData(
                name = "Ha Noi",
                image =
                "https://vietradeportal.vn/trung-tam-thanh-pho-ha-noi-o-dau/imager_11239.jpg"
            ),
            ItemData(
                name = "Can Tho",
                image =
                "https://media.vneconomy.vn/images/upload/2022/03/02/anh-thanh-pho-2-16355899477761013957557.jpg"
            ),
            ItemData(
                name = "Hai Phong",
                image =
                "https://haiphong.gov.vn/upload/haiphong/product/2020/10-2020/Hai-Phong-no-luc-doi-moi-53744.jpg"
            ),
            ItemData(
                name = "Hue",
                image =
                "https://tourdanangcity.vn/wp-content/uploads/2020/08/anh-dai-dien-thanh-pho-hue.jpg?v=1615420996"
            ),
            ItemData(
                name = "Nha Trang",
                image =
                "https://vcdn1-vnexpress.vnecdn.net/2021/03/19/NhaTrang-KhoaTran-27-1616120145.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=P6rNJD2Fm6OK-HTwBviZ4A",
            ),
            ItemData(
                name = "Thanh Hoa",
                image =
                "https://media.vneconomy.vn/images/upload/2022/02/25/thanh-hoa.jpeg"
            ),
            ItemData(
                name = "Binh Dinh",
                image =
                "https://www.icisequynhon.com/wp-content/uploads/2020/05/quynhon-binhdinh.jpg"
            ),
            ItemData(
                name = "Quang Tri",
                image =
                "https://bcp.cdnchinhphu.vn/Uploaded/nguyendieuhuong/2021_02_08/ANH3.jpg"
            ),
            ItemData(
                name = "Nghe An",
                image =
                "http://lienhehotro.vn/wp-content/uploads/2021/08/So-dien-thoai-bao-hiem-xa-hoi-Nghe-An.jpg"
            ),
            ItemData(
                name = "Hoi An",
                image =
                "https://cdn.vntrip.vn/cam-nang/wp-content/uploads/2017/08/hoi-an-quang-nam-vntrip-1.jpg"
            ),
            ItemData(
                name = "Quang Nam",
                image =
                "https://media.baodautu.vn/Images/hoanganh/2018/09/10/tinh_quang_nam.jpg"
            ),
            ItemData(
                name = "Ca Mau",
                image =
                "https://fileapi.surego.vn//Upload/NewsImage/R637096778929414583.png"
            ),
            ItemData(
                name = "An Giang",
                image =
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/T%C6%B0%E1%BB%A3ng_%C4%91%C3%A0i_B%C3%B4ng_l%C3%BAa_%E1%BB%9F_An_Giang.jpg/1200px-T%C6%B0%E1%BB%A3ng_%C4%91%C3%A0i_B%C3%B4ng_l%C3%BAa_%E1%BB%9F_An_Giang.jpg"
            ),
            ItemData(
                name = "Vung Tau",
                image =
                "https://upload.wikimedia.org/wikipedia/commons/d/dc/Vung_Tau%2C_Viet_Nam_2021.jpg"
            ),
            ItemData(
                name = "Da Lat",
                image =
                "https://vcdn-dulich.vnecdn.net/2022/02/17/273705481-488864585935710-4390-4689-3264-1645093934.jpg"
            ),
            ItemData(
                name = "Dong Nai",
                image =
                "https://upload.wikimedia.org/wikipedia/commons/8/85/Nh%C3%A0_th%E1%BB%9D_ch%C3%ADnh_V%C4%83n_mi%E1%BA%BFu_Tr%E1%BA%A5n_Bi%C3%AAn.jpg"
            ),
            ItemData(
                name = "Binh Duong",
                image =
                "https://thmyphuoc.bencat.edu.vn/uploads/thmyphuoc/news/2018_08/images2601774_bd.jpg"
            ),
            ItemData(
                name = "Quang Binh",
                image =
                "https://dulichkhampha24.com/wp-content/uploads/2020/12/kinh-nghiem-du-lich-quang-binh-c.jpg"
            )
        )
    )
}
