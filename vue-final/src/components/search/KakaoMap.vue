<template>
  <div ref="map"></div>
</template>

<script>
import { watch } from "vue";

export default {
  props: {
    places: {
      type: Array,
      required: true,
    },
    place: Object,
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
        import.meta.env.VITE_VUE_APP_JAVASCRIPT_KEY
      }`;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");

      navigator.geolocation.getCurrentPosition((position) => {
        const options = {
          center: new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude),
          level: 5,
        };

        const map = new kakao.maps.Map(container, options);

        // places props 감시
        watch(
          () => this.places,
          () => {
            this.updateCenter(map, this.places[0]);
            this.addMarkers(map);
          }
        );

        // place props 감시
        watch(
          () => this.place,
          () => {
            this.updateCenter(map, this.place);
          }
        );

        // 초기 마커 생성
        this.addMarkers();
        // center 이동
        this.updateCenter(map, this.places[0]);
      });
    },
    addMarkers(map) {
      this.places?.forEach((place) => {
        //console.log(place?.mapx, place?.mapy);
        const marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(place?.latitude, place?.longitude),
        });
        // 클릭 이벤트 리스너 추가
        kakao.maps.event.addListener(marker, "click", () => {
          this.toggleMarkerInfo(map, marker, place);
        });
        marker.setMap(map);
        //console.log(marker, map);
      });
    },
    updateCenter(map, place) {
      if (this.places.length > 0) {
        const center = new kakao.maps.LatLng(place?.latitude, place?.longitude);
        map.setCenter(center);
      }
    },
    toggleMarkerInfo(map, marker, place) {
      // 현재 열려 있는 인포윈도우가 있는지 확인하고 있으면 닫음
      if (this.infowindow) {
        this.infowindow.close();
        // 클릭한 마커와 이전에 열려 있던 마커가 같은 경우 (즉, 닫기만 하는 경우)
        if (this.selectedMarker === marker) {
          this.selectedMarker = null;
          return;
        }
      }

      // 커스텀 마커 정보 창
      const content = `
    <div style="width: 240px; display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 8px; border: 1px solid var(--primary);">
        ${place.first_image ? `<img class="custom-marker-info__image" src="${place.first_image}" alt="${place?.name}" style="width: 160px;">` : ''}
        <h3 style="font-size: 14px;">${place?.title}</h3>
    </div>
`;

      // 마커 정보 창 생성
      this.infowindow = new kakao.maps.InfoWindow({
        content: content,
        zIndex: 1, // 기본값보다 높은 zIndex를 설정하여 다른 요소 위에 표시될 수 있도록 합니다.
      });

      // 마커 위치에 정보 창 표시
      this.infowindow.open(map, marker);

      // 선택된 마커 및 인포윈도우 저장
      this.selectedMarker = marker;
    },
  },
};
</script>

<style scoped>
#map {
  width: 400px;
  height: 400px;
}
</style>
