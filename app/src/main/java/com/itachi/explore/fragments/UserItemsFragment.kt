package com.itachi.explore.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.R
import com.itachi.explore.activities.ActivityDetail
import com.itachi.explore.activities.FormActivity
import com.itachi.explore.adapters.recycler.UserItemsRecyclerAdapter
import com.itachi.explore.mvp.views.UserItemsView
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import com.itachi.explore.mvvm.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_user_items.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserItemsFragment : Fragment(){

    private fun showUserItems(items: List<ItemVO>) {
        Log.d("test---","show user items")
        rvAdapter.setNewData(items)
    }

    private fun navigateToFormActivity(itemVO: ItemVO) {
        val intent = FormActivity.newIntent(activity as Context)
        when(itemVO.item_type) {

            PAGODA_TYPE -> {
                val pagodaVO = PagodaVO(itemVO.about,itemVO.comments,itemVO.created_date,itemVO.festival_date,itemVO.is_there_festival,
                itemVO.item_id,itemVO.item_type,itemVO.photos,itemVO.title,itemVO.uploader_id)
                intent.putExtra(FormActivity.EXTRA_EVENT_ID_PAGODA,pagodaVO)
                startActivity(intent)
            }
            VIEW_TYPE -> {
                val viewVO = ViewVO(itemVO.about,itemVO.comments,itemVO.created_date,itemVO.festival_date,itemVO.is_there_festival,
                    itemVO.item_id,itemVO.item_type,itemVO.photos,itemVO.title,itemVO.uploader_id)
                intent.putExtra(FormActivity.EXTRA_EVENT_ID_VIEW,viewVO)
                startActivity(intent)
            }
            ANCIENT_TYPE -> {
                val ancientVO = AncientVO(itemVO.about,itemVO.comments,itemVO.created_date,itemVO.festival_date,itemVO.is_there_festival,
                    itemVO.item_id,itemVO.item_type,itemVO.photos,itemVO.title,itemVO.uploader_id)
                intent.putExtra(FormActivity.EXTRA_EVENT_ID_ANCIENT,ancientVO)
                startActivity(intent)
            }
        }
    }

    private fun navigateToDetailsActivity(itemVO: ItemVO) {
        val intent = ActivityDetail.newIntent(activity as Context)
        when(itemVO.item_type!!) {

            PAGODA_TYPE -> {
                val pagodaVO = PagodaVO(itemVO.about,itemVO.comments,itemVO.created_date,itemVO.festival_date,itemVO.is_there_festival,
                    itemVO.item_id,itemVO.item_type,itemVO.photos,itemVO.title,itemVO.uploader_id)
                intent.putExtra(FormActivity.EXTRA_EVENT_ID_PAGODA,pagodaVO)
                startActivity(intent)
            }
            VIEW_TYPE -> {
                val viewVO = ViewVO(itemVO.about,itemVO.comments,itemVO.created_date,itemVO.festival_date,itemVO.is_there_festival,
                    itemVO.item_id,itemVO.item_type,itemVO.photos,itemVO.title,itemVO.uploader_id)
                intent.putExtra(FormActivity.EXTRA_EVENT_ID_VIEW,viewVO)
                startActivity(intent)
            }
            ANCIENT_TYPE -> {
                val ancientVO = AncientVO(itemVO.about,itemVO.comments,itemVO.created_date,itemVO.festival_date,itemVO.is_there_festival,
                    itemVO.item_id,itemVO.item_type,itemVO.photos,itemVO.title,itemVO.uploader_id)
                intent.putExtra(FormActivity.EXTRA_EVENT_ID_ANCIENT,ancientVO)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_items,container,false)
    }

    private lateinit var rvAdapter : UserItemsRecyclerAdapter

    private val mViewModel: UserProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPagodaRecyclerView(view.context)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                mViewModel.itemListStateFlow.collect {
//                    showUserItems(it)
//                }
//            }
//        }
        mViewModel.itemListLiveData.observe(viewLifecycleOwner){
            showUserItems(it)
        }

        mViewModel.userVOLiveData.observe(viewLifecycleOwner) {
            mViewModel.getUserItems(it)
        }
    }


    private fun setUpPagodaRecyclerView (context : Context) {
        rvAdapter = UserItemsRecyclerAdapter(
            {
                rvAdapter.notifyDataSetChanged()
            },
            {editBtnPosition->
                navigateToFormActivity(mViewModel.onClickedEditItemBtn(editBtnPosition))
            },
            {viewBtnPosition->
                navigateToDetailsActivity(mViewModel.onClickedViewItemBtn(viewBtnPosition))
            }
        )

        with(rv_user_items) {
            setHasFixedSize(true)
            adapter = rvAdapter
            val layoutManager = GridLayoutManager(context,2)
            (layoutManager as GridLayoutManager).scrollToPosition(0) // this line solved the problem that recyclerview auto scroll up
            layoutManager.reverseLayout = false
            rv_user_items.layoutManager = layoutManager
        }
    }
}