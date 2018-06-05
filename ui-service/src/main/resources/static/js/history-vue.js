new Vue({
    el: '#root',
    data() {
        return {
            rows: null,
        }
    },
    mounted() {
        this.fill()
    },
    methods: {
        fill: function () {
            axios
                .get('/historyMock')
                .then(response => (this.rows = response.data))
        }
    }
})