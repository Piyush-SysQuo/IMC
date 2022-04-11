package sa.med.imc.myimc.webView;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import sa.med.imc.myimc.Base.BaseFragment;
import sa.med.imc.myimc.Interfaces.FragmentListener;
import sa.med.imc.myimc.databinding.FragmentWebViewBinding;


public class WebViewFragment extends BaseFragment implements WebViewViews{
    public static String LINK = "link";
    public static String TITLE = "title";
    public static String REF = "ref";
    private FragmentWebViewBinding binding;
    private WebViewPresenter webViewPresenter;


    public WebViewFragment() {

    }


    public static WebViewFragment newInstance(String link, String title) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(LINK, link);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public static WebViewFragment newInstance(String link, String title,String ref) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(LINK, link);
        args.putString(TITLE, title);
        args.putString(REF, ref);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewPresenter= new WebViewPresenterImpl(requireActivity(),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // getCallBack().hideNavigation();
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (getArguments()!=null){
            if (getArguments().containsKey(TITLE)){
                binding.toolbar.setTitle(getArguments().getString(TITLE));
            }
            if (getArguments().containsKey(LINK)){
                loadWebView(getArguments().getString(LINK));
            }
        }
        binding.toolbar.setNavigationOnClickListener(v->{
            getCallBack().backPressed(WebViewFragment.class.getSimpleName());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  getCallBack().showNavigation();
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void loadWebView(String url) {
        WebView webView = binding.webView;
        webView.invalidate();
//        webView.clearCache(true);
//        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(50);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setLayerType(View.LAYER_TYPE_NONE, null);
        webView.getSettings().setDomStorageEnabled(true);
        if (url.contains("matterport")) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("prescription-payement-authorized")){
                    if (getArguments()!=null && getArguments().containsKey(REF)) {
                        webViewPresenter.validatePaymentRef(getArguments().getString(REF));
                    }
                    else{
                        getCallBack().openHome();
                    }

                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    @Override
    public void onValidatePaymentRef() {
        getCallBack().openHome();
    }
}