package com.example.gymfitness.fragments.resources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.resources.ArticleDetailAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.databinding.FragmentArticleDetailBinding;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.viewmodels.ArticleDetailViewModel;

import java.util.ArrayList;

public class ArticleDetailFragment extends Fragment {
    private ArticleDetailAdapter adapter;
    private ArticleDetailViewModel articleDetailViewModel;
    private FragmentArticleDetailBinding binding;
    public ArticleDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_detail, container, false);

        articleDetailViewModel = new ViewModelProvider(this).get(ArticleDetailViewModel.class);
        binding.setViewModel(articleDetailViewModel);
        binding.setLifecycleOwner(this);

        adapter = new ArticleDetailAdapter(new ArrayList<>());
        binding.rvArticleDetail.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvArticleDetail.setAdapter(adapter);

        if (getArguments() != null) {
            String articleTitle = getArguments().getString("articleTitle");
            articleDetailViewModel.loadArticleDetails(articleTitle);
        }

        articleDetailViewModel.getArticleDetails().observe(getViewLifecycleOwner(), details -> {
            adapter.setArticleDetails(details);
            adapter.notifyDataSetChanged();
        });

        articleDetailViewModel.getThumbnail().observe(getViewLifecycleOwner(), thumbnail -> {
            Glide.with(requireContext()).load(thumbnail).into(binding.imgArticle);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        articleDetailViewModel.getArticles().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null && !articles.isEmpty()) {
                Article firstArticle = articles.get(0);
                FavoriteHelper.checkFavorite(firstArticle, getContext(), binding.imgFavStar);

                binding.imgFavStar.setOnClickListener(v ->
                        FavoriteHelper.setFavorite(firstArticle, v.getContext(), binding.imgFavStar)
                );
            }
        });
    }
}
