package com.sandrocaseiro.template.exceptions

class ItemNotFoundException : BaseException() {
    override val error = CustomErrors.ITEM_NOT_FOUND_ERROR
}
