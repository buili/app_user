package com.example.quanly.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanly.Interface.ItemClickDeleteListener;
import com.example.quanly.R;
import com.example.quanly.adapter.DonHangAdapter;
import com.example.quanly.model.DonHang;
import com.example.quanly.model.Item;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link DonMuaFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DonMuaFragment extends Fragment {

//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public DonMuaFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DonMuaFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DonMuaFragment newInstance(String param1, String param2) {
//        DonMuaFragment fragment = new DonMuaFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recyclerView;
    TextView txt_trong;
    static final String DON = "don";
    String trangthai;


    public static DonMuaFragment newInstance(String status) {
        DonMuaFragment fragment = new DonMuaFragment();
        Bundle args = new Bundle();
        args.putString(DON, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trangthai = getArguments().getString(DON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_mua, container, false);
        recyclerView = view.findViewById(R.id.recycleview_donmua);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);


        txt_trong = view.findViewById(R.id.txtdonmua);

        int trangthaiInt = getStatusAsInt(trangthai);
        donHang(trangthaiInt);
        return view;

    }

    private int getStatusAsInt(String status) {
        for (int i = 0; i < 5; i++) {
            if (Utils.statusOrder(i).equals(status)) {
                return i;
            }
        }
        return -1;
    }


    private void donHang(int trangthai) {
        compositeDisposable.add(apiBanHang.donhangtheotrangthai(Utils.user_current.getId(), trangthai)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                donHangModel -> {
                                    Log.d("DonHangActivity", "DonHangModel: " + donHangModel.getResult());
                                    List<DonHang> donHangList = donHangModel.getResult();
                                    if (donHangList == null || donHangList.isEmpty()) {
                                    
                                        recyclerView.setVisibility(View.GONE);
                                        txt_trong.setVisibility(View.VISIBLE);
                                    } else {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        txt_trong.setVisibility(View.GONE);
                                        DonHangAdapter donHangAdapter = new DonHangAdapter(getContext(), donHangList, new ItemClickDeleteListener() {
                                            //                                            @Override
//                                            public void onClickDelete(int iddonhang) {
//
//                                            }
                                            @Override
                                            public void onClickDelete(View view, int iddonhang) {

                                            }
                                        });
                                        recyclerView.setAdapter(donHangAdapter);
                                        donHangAdapter.notifyDataSetChanged();
                                    }
                                },
                                throwable -> {
                                    Log.e("DonHangActivity", "Lỗi: " + throwable);
                                    Toast.makeText(getContext(), "Lỗi " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }


}