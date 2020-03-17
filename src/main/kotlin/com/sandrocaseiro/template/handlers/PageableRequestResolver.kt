package com.sandrocaseiro.template.handlers

import com.sandrocaseiro.template.exceptions.PageableBadRequestException
import com.sandrocaseiro.template.models.DPageable
import com.sandrocaseiro.template.utils.toInt
import org.springframework.core.MethodParameter
import org.springframework.lang.Nullable
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.regex.Matcher
import java.util.regex.Pattern

class PageableRequestResolver : HandlerMethodArgumentResolver {
    private val SORT_PATTERN = "^(\\-?)(.*)"
    private val OFFSET_PARAM = "\$pageoffset"
    private val LIMIT_PARAM = "\$pagelimit"
    private val SORT_PARAM = "\$sort"

    private val pattern: Pattern = Pattern.compile(SORT_PATTERN)

    override fun supportsParameter(methodParameter: MethodParameter): Boolean = methodParameter.parameterType == DPageable::class

    override fun resolveArgument(methodParameter: MethodParameter, @Nullable mavContainer: ModelAndViewContainer?,
                                 nativeWebRequest: NativeWebRequest, @Nullable binderFactory: WebDataBinderFactory?): Any? {
        val pageOffset: Int
        try {
            pageOffset = nativeWebRequest.getParameter(OFFSET_PARAM).toInt(DPageable.CURRENT_PAGE)
        }
        catch (e: NumberFormatException) {
            throw PageableBadRequestException(OFFSET_PARAM, e)
        }
        
        val pageLimit: Int
        try {
            pageLimit = nativeWebRequest.getParameter(LIMIT_PARAM).toInt(DPageable.PAGE_SIZE)
        }
        catch (e: NumberFormatException) {
            throw PageableBadRequestException(LIMIT_PARAM, e);
        }

        if (pageOffset <= 0)
            throw PageableBadRequestException(OFFSET_PARAM);

        val sort: String? = nativeWebRequest.getParameter(SORT_PARAM)
        var requestSortFields = emptyList<String>()
        if (!sort.isNullOrEmpty())
            requestSortFields = sort.split(",")

        val sortFields = mutableListOf<DPageable.Sort>()
        for (sortField: String in requestSortFields) {
            val matcher: Matcher = pattern.matcher(sortField.trim())
            if (!matcher.matches())
                throw PageableBadRequestException(SORT_PARAM)

            val signal: String = matcher.group(1)
            val field: String = matcher.group(2)

            if (signal.isEmpty() && "-" != signal)
                throw PageableBadRequestException(SORT_PARAM);

            sortFields.add(DPageable.Sort(field, "-" == signal));
        }

        return DPageable(pageOffset, pageLimit, sortFields);
    }
}
