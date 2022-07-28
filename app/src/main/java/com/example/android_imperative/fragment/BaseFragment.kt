package com.example.android_imperative.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes)