package com.kassaev.notes.navigation

sealed class Screen(val route: String) {
    data object MainScreen: Screen("mainScreen")
    data object MainScreenDetail: Screen("mainScreenDetail"){
        fun withArg(arg: String): String{
            return (this.route + "?noteId=$arg")
        }
    }
    data object RecycleBinDetailScreen: Screen("recycleBinDetailScreen"){
        fun withArg(arg: String): String{
            return (this.route + "/$arg")
        }
    }
}