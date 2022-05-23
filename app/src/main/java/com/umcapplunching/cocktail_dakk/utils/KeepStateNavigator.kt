package com.umcapplunching.cocktail_dakk.utils

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

//  내비게이터의 이름으로,
// graph.xml에서 <keep_state_fragment>와 같은 형태로 쓰일 태그의 이름을 설정한다고 보면 된다.
@Navigator.Name("keep_state_fragment")
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int,
) : FragmentNavigator(context, manager, containerId) {
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {

        // destination tag
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()
        var initialNavigate = false
        val currentFragment = manager.primaryNavigationFragment

        // primaryNavigationFragment가 존재하면 기존 primaryFramgent hide 처리 (재생성 방지)
        if (currentFragment != null){
            transaction.hide(currentFragment)
        }else{
            initialNavigate = true
        }
        var fragment = manager.findFragmentByTag(tag)
        // 최초로 생성되는 fragment
        if(fragment==null){
            // add로 fargment 최초 생성
            val className = destination.className
            fragment = manager.fragmentFactory.instantiate(context.classLoader,className)
            transaction.add(containerId,fragment,tag)
        }else{
            // 이미 생성되어 있던 fragment라면 show
            transaction.show(fragment)
        }

        // destination fragment를 primary로 설정
        transaction.setPrimaryNavigationFragment(fragment)

        // transaction 관련 fragment 상태 변경 최적화
        transaction.setReorderingAllowed(true)
        transaction.commit()

        return if(initialNavigate){
            destination
        }else{
            null
        }
    }
}