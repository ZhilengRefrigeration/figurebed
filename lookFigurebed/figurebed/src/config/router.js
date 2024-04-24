import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: () =>
                import ("@/components/Login.vue")
        },
        {
            path: '/home',
            component: () =>
                import ("../components/Home.vue"),
            children: [{
                path: '/home/main',
                component: () =>
                    import ("@/components/children/Main.vue")
            },
                {
                    path: '/home/pin',
                    component: () =>
                        import ("@/components/children/PinPage.vue")
                },
                {
                    path: '/home/ran',
                    component: () =>
                        import ("@/components/children/RanPage.vue")
                },
                {
                    path: '/home/ranking',
                    component: () =>
                        import("@/components/children/Echarts.vue")

                }

            ]
        },
    ]
})
export default router