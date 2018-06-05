// noinspection JSUnusedGlobalSymbols
new Vue({
    el: '#root',
    data() {
        return {
            numberOfViews: null,
            rows: null,
            timePeriod: null
        }
    },
    mounted() {
        this.fill('90')
    },
    methods: {
        fill: function (message) {
            // noinspection JSUnusedGlobalSymbols
            axios
                .get('/test?period=' + message)
                .then(response => (this.numberOfViews = response.data));
            // noinspection JSUnusedGlobalSymbols
            axios
                .get('/statistics?period=' + message)
                .then(response => (this.rows = response.data));
            if (message === '7') {
                // noinspection JSUnusedGlobalSymbols
                this.timePeriod = 'Last 7 days'
            } else if (message === '30') {
                // noinspection JSUnusedGlobalSymbols
                this.timePeriod = 'Last 30 days'
            } else if (message === '90') {
                // noinspection JSUnusedGlobalSymbols
                this.timePeriod = 'Last 3 months'
            }
        }
    }
});