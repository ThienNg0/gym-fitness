package com.example.gymfitness.fragments.resources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.resources.ArticleDetailAdapter;
import com.example.gymfitness.databinding.FragmentArticleDetailBinding;
import com.example.gymfitness.databinding.FragmentArticleResourceDetailBinding;
import com.example.gymfitness.viewmodels.ArticleDetailResourceViewModel;

import java.util.ArrayList;

public class ArticleResourceDetailFragment extends Fragment {
    private ArticleDetailAdapter adapter;
    private ArticleDetailResourceViewModel articleDetailResourceViewModel;
    private FragmentArticleResourceDetailBinding  binding;
    public ArticleResourceDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_resource_detail, container, false);
        articleDetailResourceViewModel = new ViewModelProvider(this).get(ArticleDetailResourceViewModel.class);
        binding.setViewModel(articleDetailResourceViewModel);
        binding.setLifecycleOwner(this);

        adapter = new ArticleDetailAdapter(new ArrayList<>());
        binding.rvArticleDetail.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvArticleDetail.setAdapter(adapter);

        if (getArguments() != null) {
            String articleTitle = getArguments().getString("articleTitle");
            articleDetailResourceViewModel.loadArticleDetails(articleTitle);
        }

        articleDetailResourceViewModel.getArticleDetails().observe(getViewLifecycleOwner(), details -> {
            adapter.setArticleDetails(details);
            adapter.notifyDataSetChanged();
        });

        articleDetailResourceViewModel.getThumbnail().observe(getViewLifecycleOwner(), thumbnail -> {
            Glide.with(requireContext()).load(thumbnail).into(binding.imgArticle);
        });

        return binding.getRoot();


    }
}